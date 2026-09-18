"""Run this once locally to log in and print a portable session string.

Paste the printed string into the TG_SESSION_STRING GitHub secret so the
poll.py workflow can authenticate without an interactive login.
"""

from telethon.sessions import StringSession
from telethon.sync import TelegramClient

import config

with TelegramClient(StringSession(), config.API_ID, config.API_HASH) as client:
    session_string = client.session.save()
    print("\nTG_SESSION_STRING (keep this secret, treat it like a password):\n")
    print(session_string)
    print()
