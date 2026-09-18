"""Stateless single-pass check, meant to be run on a schedule (GitHub Actions).

Unlike main.py (which stays connected and listens for events), this script
connects, fetches messages newer than the last-seen id, checks them against
the keyword list, forwards matches, records the new last-seen id, and exits.
"""

import asyncio
import json
import logging
from pathlib import Path

from telethon import TelegramClient
from telethon.sessions import StringSession

import config

logging.basicConfig(
    level=logging.INFO,
    format="%(asctime)s [%(levelname)s] %(message)s",
)
log = logging.getLogger("veolia-filter-poll")

STATE_FILE = Path(config.STATE_FILE)


def load_last_id() -> int | None:
    if not STATE_FILE.exists():
        return None
    return json.loads(STATE_FILE.read_text()).get("last_message_id")


def save_last_id(message_id: int) -> None:
    STATE_FILE.write_text(json.dumps({"last_message_id": message_id}))


def matched_keywords(text: str) -> list[str]:
    if not text:
        return []
    lowered = text.lower()
    return [kw for kw in config.KEYWORDS if kw.lower() in lowered]


async def main() -> None:
    if not config.SESSION_STRING:
        raise RuntimeError(
            "TG_SESSION_STRING is not set. Run generate_session.py locally once "
            "and store its output as the TG_SESSION_STRING secret."
        )

    client = TelegramClient(StringSession(config.SESSION_STRING), config.API_ID, config.API_HASH)
    await client.start()

    entity = await client.get_entity(config.SOURCE_CHANNEL)
    last_id = load_last_id()

    if last_id is None:
        latest = await client.get_messages(entity, limit=1)
        newest_id = latest[0].id if latest else 0
        save_last_id(newest_id)
        log.info("First run, no prior state — baseline set to message %s", newest_id)
        await client.disconnect()
        return

    new_messages = [
        message async for message in client.iter_messages(entity, min_id=last_id, reverse=True)
    ]

    if not new_messages:
        log.info("No new messages since id %s", last_id)
        await client.disconnect()
        return

    channel_username = getattr(entity, "username", None)
    highest_id = last_id

    for message in new_messages:
        highest_id = max(highest_id, message.id)
        hits = matched_keywords(message.raw_text)
        if not hits:
            continue

        log.info("Match (%s) in message %s", ", ".join(hits), message.id)
        link = f"https://t.me/{channel_username}/{message.id}" if channel_username else None
        header = f"🔎 Նշված բառեր՝ {', '.join(hits)}"
        if link:
            header += f"\n{link}"

        await client.send_message(config.TARGET_CHAT, header)
        await client.forward_messages(config.TARGET_CHAT, message)

    save_last_id(highest_id)
    log.info("Checked %d new message(s), state advanced to %s", len(new_messages), highest_id)
    await client.disconnect()


if __name__ == "__main__":
    asyncio.run(main())
