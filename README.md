
# WATmerch - Android App (work in progress)
## Description
Android application for viewing, ordering and managing the store's products of WAT merchandise. It is based on communication with an external API created by my colleague (https://github.com/qsspy/WATmerch-backend), which uses Java Spring Boot and MySQL database. To use some API endpoints, the application must provide authorization, which has been implemented.

![watmerch_part1](https://user-images.githubusercontent.com/59984158/120124126-001adf80-c1b3-11eb-823f-abe66ec15702.gif) ![watmerch_part2](https://user-images.githubusercontent.com/59984158/120124481-e5496a80-c1b4-11eb-874f-bf7c98b47e8e.gif)


## Features
- Signing in, up and out the application.
- Using authorization when communicating with API.
- Filtering products by category and given phrase (remote).
- Adding and removing products from the cart (local).
- Creating orders.
- User panel with the possibility of editing data.
- Changing app colors (dark & light theme).
- Two languages to choose from: Polish and English.
- Employee panel with possibility of scanning products by barcode.
- PUSH notifications.

## Used technologies and frameworks
- <b>Kotlin</b> in Android Studio
- <b>Room</b> with SQLite
- <b>Retrofit</b> with OkHttp
- <b>Hilt</b>
- Kotlin Coroutines
- MVVM
- Data Binding & LiveData
- Material Design
- Stomp Protocol Android (https://github.com/NaikSoftware/StompProtocolAndroid)
- Barcode scanner (https://github.com/journeyapps/zxing-android-embedded)

## Screenshots

![watmerch_screens](https://user-images.githubusercontent.com/59984158/117128330-910dbf00-ad9d-11eb-8191-19c5dceb5a19.png)
