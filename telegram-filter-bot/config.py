import os

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
SESSION_NAME = os.getenv("TG_SESSION_NAME", "veolia_filter_session")

SOURCE_CHANNEL = _clean_channel(os.getenv("SOURCE_CHANNEL", "VeoliaJur"))
TARGET_CHAT = os.getenv("TARGET_CHAT", "me").strip()

KEYWORDS = [
    kw.strip()
    for kw in os.getenv("KEYWORDS", "Առնո Բաբաջանյան,Օհանով").split(",")
    if kw.strip()
]
