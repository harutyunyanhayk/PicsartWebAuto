# Veolia Jur filter bot

Telethon-ով գրված userbot, որը հետևում է https://t.me/VeoliaJur ալիքի նոր
հաղորդագրություններին, ֆիլտրում է դրանք ըստ բանալի բառերի (լռելյայն՝
«Առնո Բաբաջանյան» և «Օհանով») և համընկնումների դեպքում ուղարկում է քեզ
Telegram-ով (լռելյայն՝ քո Saved Messages-ը):

Քանի որ VeoliaJur-ը հասարակական ալիք է, բայց Bot API-ով բոտերը ալիքների
հաղորդագրությունները ինքնուրույն չեն կարող կարդալ առանց ադմինի ավելացման,
սկրիպտն աշխատում է որպես քո սեփական Telegram հաշվի "userbot" (Telegram
Client API, Telethon գրադարանով):

Երկու եղանակով կարող է աշխատել.

- **`poll.py` + GitHub Actions** (առաջարկվող) — ոչինչ չի պահանջում քեզնից
  անընդհատ բաց պահել. GitHub-ը ամեն 10 րոպեն մեկ ինքն է գործարկում
  ստուգումը, անվճար։
- **`main.py`** — երկարաժամկետ միացած մնացող սկրիպտ, որը պետք է աշխատի քո
  համակարգչում կամ սերվերի վրա անընդհատ (իրական ժամանակում, առանց ուշացման):

## 1. GitHub Actions-ով (առաջարկվող, ոչինչ քո կողմից բաց պահել պետք չէ)

1. Ստացիր `api_id` և `api_hash`՝ https://my.telegram.org → "API development tools"։

2. Տեղական համակարգչում (միայն մեկ անգամ, մուտք գործելու համար) գեներացրու
   session string:

   ```bash
   cd telegram-filter-bot
   python -m venv venv && source venv/bin/activate
   pip install -r requirements.txt
   echo "TG_API_ID=123456" > .env
   echo "TG_API_HASH=your_api_hash" >> .env
   python generate_session.py
   ```

   Կպահանջի հեռախոսահամար, Telegram-ից եկած կոդ, և 2FA գաղտնաբառ (եթե
   ունես)։ Վերջում կտպի մի երկար string՝ `TG_SESSION_STRING`։ Դա քո հաշվի
   մուտքի token-ն է, պահիր գաղտնի։

3. GitHub-ում՝ repo → **Settings → Secrets and variables → Actions**:

   - **Secrets** ներդիրում ավելացրու.
     - `TG_API_ID`
     - `TG_API_HASH`
     - `TG_SESSION_STRING` (նախորդ քայլի արդյունքը)
   - **Variables** ներդիրում (ոչ-գաղտնի, ուստի կարող ես թողնել կամ
     փոփոխել).
     - `SOURCE_CHANNEL` = `VeoliaJur`
     - `TARGET_CHAT` = `me`
     - `KEYWORDS` = `Առնո Բաբաջանյան,Օհանով`

4. Workflow-ը (`.github/workflows/veolia-filter-bot.yml`) աշխատում է
   ամեն 10 րոպեն մեկ (`cron`) և ինքնուրույն commit է անում իր վիճակի
   ֆայլը (`telegram-filter-bot/state.json`)՝ որպեսզի հաջորդ գործարկման
   ժամանակ իմանա, թե որ հաղորդագրությունից է պետք շարունակել։ Ձեռքով էլ
   կարող ես գործարկել՝ **Actions → Veolia Jur filter bot → Run workflow**։

   > Կարևոր. GitHub-ի scheduled workflow-ները աշխատում են միայն repo-ի
   > default branch-ից (`master`)։ Մինչև այս PR-ը merge չլինի `master`-ին,
   > cron-ը ինքնաբերաբար չի գործարկվի, բայց ձեռքով (`workflow_dispatch`)
   > կարող ես փորձարկել նաև այս branch-ի վրա։

## 2. Տեղական, անընդհատ աշխատող տարբերակ (`main.py`)

Օգտագործիր սա, եթե ունես մեքենա/սերվեր, որը կարող ես մշտապես բաց պահել,
և ուզում ես գրեթե ակնթարթային (real-time) ծանուցում՝ առանց GitHub Actions-ի
10-րոպեանոց ուշացման։

1. Ստացիր `api_id` և `api_hash`՝ https://my.telegram.org → "API development tools"

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

4. Գործարկիր.

   ```bash
   python main.py
   ```

   Առաջին անգամ կպահանջի հեռախոսահամար և մուտքի կոդ, հետո կստեղծվի
   `*.session` ֆայլ, և կրկին մուտք գործելու կարիք չի լինի։

   Երկարաժամկետ աշխատելու համար օգտագործիր `systemd`, `tmux`, կամ
   `nohup python main.py &`։

## Կարգավորումներ

| Փոփոխական | Օգտագործվում է | Նշանակություն |
|---|---|---|
| `TG_API_ID`, `TG_API_HASH` | երկուսում | Քո Telegram API տվյալները |
| `TG_SESSION_NAME` | `main.py` | Session ֆայլի անունը |
| `TG_SESSION_STRING` | `poll.py` | `generate_session.py`-ից ստացված login token |
| `SOURCE_CHANNEL` | երկուսում | Հետևվող ալիքը (լռելյայն՝ `VeoliaJur`) |
| `TARGET_CHAT` | երկուսում | Ում ուղարկել համընկնումները. `me`՝ Saved Messages, կամ username/chat id |
| `KEYWORDS` | երկուսում | Ստորակետով բաժանված բանալի բառեր (case-insensitive, substring) |
| `STATE_FILE` | `poll.py` | Որտեղ պահել վերջին ստուգված հաղորդագրության id-ն (լռելյայն՝ `state.json`) |

> Նշում. հարցումում «Օհանով» անունը գրված էր երկու անգամ (կրկնված): Ես
> թողել եմ մեկ գրառում `Օհանով`-ի համար։ Եթե նկատի ունեիր երկրորդ, տարբեր
> ազգանուն, պարզապես ավելացրու այն `KEYWORDS`-ի ցանկում, ստորակետով
> բաժանված (օրինակ՝ `Առնո Բաբաջանյան,Օհանով,Ազգանուն2`):
