# Scrimmage Rewards

This README provides step-by-step instructions on how to integrate
the Scrimmage Rewards program into your Android application.

## Prerequisites

Before integrating the Scrimmage Rewards program into your Android application,
ensure that you have the following prerequisites in place:

- Android Studio (or any other compatible development environment) installed on your machine.
- Access to the Scrimmage Rewards program and an active account.
- A backend server that can return a valid token for the Scrimmage Rewards program.

## Integration Steps

Follow the steps below to integrate the Scrimmage Rewards program into your Android application:

1. **Install the WebView Support Library**

   To enable the Scrimmage Rewards program to display its content within a WebView,
   you need to install the WebView support library. This library allows you to embed
   web content within your Android application.

2. **Add the JavaScript Enabled Property**

   In order for the Scrimmage Rewards program to function correctly,
   you must enable JavaScript in the WebView that will display
   the program's content. To do this, set the `javaScriptEnabled` property
   of the WebView to `true`. This will ensure that the JavaScript code used
   by the Scrimmage Rewards program can be executed.

   Check [MainActivity.kt](./app/src/main/java/co/scrimmage/rewards/androidexample/MainActivity.kt) for example code.

3. **Add token retrieval logic for user authentication**

   The Scrimmage Rewards program requires a valid token to authenticate
   the user and display the program's content. To retrieve a valid token,
   you must implement the logic to retrieve the token from your backend server.
   Once you have retrieved the token, you can pass it to the Scrimmage Rewards
   program to authenticate the user and display the program's content.

4. **Ensure WebView Is Not Displayed Until Token Is Available**

   It is essential to ensure that the WebView displaying the Scrimmage Rewards
   program's content is not visible until a valid token is available.
