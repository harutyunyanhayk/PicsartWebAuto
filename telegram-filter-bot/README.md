# Veolia Jur filter bot

Telethon-ով գրված userbot, որը հետևում է https://t.me/VeoliaJur ալիքի նոր
հաղորդագրություններին, ֆիլտրում է դրանք ըստ բանալի բառերի (լռելյայն՝
«Առնո Բաբաջանյան» և «Օհանով») և համընկնումների դեպքում ուղարկում է քեզ
Telegram-ով (լռելյայն՝ քո Saved Messages-ը):

Քանի որ VeoliaJur-ը հասարակական ալիք է, բայց Bot API-ով բոտերը ալիքների
հաղորդագրությունները ինքնուրույն չեն կարող կարդալ առանց ադմինի ավելացման,
սկրիպտն աշխատում է որպես քո սեփական Telegram հաշվի "userbot" (Telegram
Client API, Telethon գրադարանով):

## Տեղադրում

1. Ստացիր `api_id` և `api_hash`՝ https://my.telegram.org → "API development tools":
2. Պատճենիր `.env.example`-ը `.env`-ի մեջ և լրացրու:

   ```bash
   cp .env.example .env
   ```

3. Տեղադրիր կախվածությունները (ցանկալի է virtualenv-ում):

   ```bash
   python -m venv venv
   source venv/bin/activate
   pip install -r requirements.txt
   ```

4. Առաջին անգամ գործարկելիս Telethon-ը կպահանջի քո հեռախոսահամարը և
   Telegram-ի կողմից ուղարկված մուտքի կոդը (և, եթե ունես, երկքայլանոց
   ստուգման գաղտնաբառը). Դրանից հետո կստեղծվի `*.session` ֆայլ, և կրկին
   մուտք գործելու կարիք չի լինի:

   ```bash
   python main.py
   ```

## Կարգավորումներ (`.env`)

| Փոփոխական | Նշանակություն |
|---|---|
| `TG_API_ID`, `TG_API_HASH` | Քո Telegram API տվյալները |
| `TG_SESSION_NAME` | Session ֆայլի անունը |
| `SOURCE_CHANNEL` | Հետևվող ալիքը (լռելյայն՝ `VeoliaJur`) |
| `TARGET_CHAT` | Ում ուղարկել համընկնումները. `me`՝ Saved Messages, կամ username/chat id |
| `KEYWORDS` | Ստորակետով բաժանված բանալի բառեր (case-insensitive, substring) |

> Նշում. հարցումում «Օհանով» անունը գրված էր երկու անգամ (կրկնված): Ես
> թողել եմ մեկ գրառում `Օհանով`-ի համար։ Եթե նկատի ունեիր երկրորդ, տարբեր
> ազգանուն, պարզապես ավելացրու այն `.env`-ի `KEYWORDS` ցանկում, ստորակետով
> բաժանված (օրինակ՝ `Առնո Բաբաջանյան,Օհանով,Ազգանուն2`):

## Աշխատեցում ֆոնային ռեժիմում

Երկարաժամկետ աշխատելու համար կարող ես օգտագործել `systemd`, `tmux`, կամ
`nohup python main.py &`։
