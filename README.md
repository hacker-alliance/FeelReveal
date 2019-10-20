# Feel Reveal
Feel Reveal is a mobile app that helps those on the autism spectrum better integrate into society by helping them interpret emotions through the analysis of facial expressions.



## Dependencies 
Before building, Android Studio needs the NDK.

To set this up, follow these steps:
1. Go to File -> Project Structure -> SDK Location
2. Click Download NDK if you don't already have it and complete the download
3. Record/Save the installation directory path
4. Go to the root of the repo and if the `local.properties` file doesn't exist, create it.
5. Open `local.properties`
6. It should have some code like `sdk.dir=<path to sdk>`. On the next line put `ndk.dir=<path to ndk>`

After this is done and saved, building shouldn't be an issue.

Also, don't update gradle or you'll break everything :]