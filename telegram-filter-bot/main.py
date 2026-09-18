import logging

from telethon import TelegramClient, events

import config

logging.basicConfig(
    level=logging.INFO,
    format="%(asctime)s [%(levelname)s] %(message)s",
)
log = logging.getLogger("veolia-filter-bot")

client = TelegramClient(config.SESSION_NAME, config.API_ID, config.API_HASH)


def matched_keywords(text: str) -> list[str]:
    if not text:
        return []
    lowered = text.lower()
    return [kw for kw in config.KEYWORDS if kw.lower() in lowered]


@client.on(events.NewMessage(chats=config.SOURCE_CHANNEL))
async def on_new_message(event: events.NewMessage.Event) -> None:
    hits = matched_keywords(event.raw_text)
    if not hits:
        return

    log.info("Match (%s) in message %s", ", ".join(hits), event.id)

    chat = await event.get_chat()
    channel_username = getattr(chat, "username", None)
    link = f"https://t.me/{channel_username}/{event.id}" if channel_username else None

    header = f"🔎 Նշված բառեր՝ {', '.join(hits)}"
    if link:
        header += f"\n{link}"

    await client.send_message(config.TARGET_CHAT, header)
    await client.forward_messages(config.TARGET_CHAT, event.message)


async def main() -> None:
    await client.start()
    me = await client.get_me()
    log.info("Logged in as %s", getattr(me, "username", None) or me.id)
    log.info(
        "Watching '%s' for keywords: %s -> delivering to '%s'",
        config.SOURCE_CHANNEL,
        ", ".join(config.KEYWORDS),
        config.TARGET_CHAT,
    )
    await client.run_until_disconnected()


if __name__ == "__main__":
    with client:
        client.loop.run_until_complete(main())
