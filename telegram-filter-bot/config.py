import os
from pathlib import Path

from dotenv import load_dotenv

load_dotenv()


def _require(name: str) -> str:
    value = os.getenv(name)
    if not value:
        raise RuntimeError(f"Missing required environment variable: {name}")
    return value


def _clean_channel(raw: str) -> str:
    raw = raw.strip()
    for prefix in ("https://t.me/", "http://t.me/", "t.me/", "@"):
        if raw.startswith(prefix):
            return raw[len(prefix):]
    return raw


API_ID = int(_require("TG_API_ID"))
API_HASH = _require("TG_API_HASH")

# Used by main.py (persistent listener, local run): a session file on disk.
SESSION_NAME = os.getenv("TG_SESSION_NAME", "veolia_filter_session")

# Used by poll.py (GitHub Actions, stateless run): a portable session string,
# generated once locally with generate_session.py.
SESSION_STRING = os.getenv("TG_SESSION_STRING", "")

# Where poll.py persists the id of the last message it has already checked.
STATE_FILE = os.getenv("STATE_FILE", str(Path(__file__).parent / "state.json"))

SOURCE_CHANNEL = _clean_channel(os.getenv("SOURCE_CHANNEL", "VeoliaJur"))
TARGET_CHAT = os.getenv("TARGET_CHAT", "me").strip()

KEYWORDS = [
    kw.strip()
    for kw in os.getenv("KEYWORDS", "Առնո Բաբաջանյան,Օհանով").split(",")
    if kw.strip()
]
