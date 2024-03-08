# Groww Beats - Android Application

This application is developed as a part of a recruitment assignment for Groww.
This README file provides an overview of the application, including information on technologies used, an APK file, and Demo video.

## Table of Contents

- [Technologies Used](#technologies-used)
- [Demo Video](#demo-video)
- [Imp Decisions](#decisions)
- [APK Download](#apk-download)

## Technologies Used

- Clean MVVM Architecture preferred by Google
- 100% Kotlin
- Navigation Components for Navigation between fragments
- Koin: Multiplatform dependency injection framework (Used instead of Dagger in the interest of time)
- Kotlin Coroutines for Asynchronous work
- Room Database as local db for storing Recent Searches
- Retrofit for making API requests
- Gson for converting between JSON strings and objects
- Glide: for image loading and caching library
- XML, UI Design, Transitions, and Animations
- Object Oriented Design principles (SOLID principles, Design Patterns, separation of concern, reusability, etc)

## Demo Video

To see a demonstration of the Groww Beats, please watch the following demo video:

[Demo Video](https://drive.google.com/file/d/1GnUZS4UNx1JEgBB23IqLxSsfMBSBkkRY/view?usp=sharing)

## Imp Decisions

- Used debouncing effect for search API calls (This saves more than 50% of requests)
- Token management (Automatic token refreshment, if the authToken is expired)
- Optimized offline storage (table stores only 'N' recent records)
- etc

## APK Download

To download the debug APK of the Groww Beats Android application, click the link below:

[Download APK](https://drive.google.com/file/d/1KmteEtROvm3MuX-0IFf1OFwCyorl7rkC/view?usp=sharing)
