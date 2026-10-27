Souad Love Game — GitHub APK

1. Upload the CONTENTS of this ZIP to the root of a GitHub repository, including .github/workflows/build-apk.yml (do not add an extra outer folder).
2. In GitHub open Actions > Build Android APK > Run workflow.
3. After the workflow succeeds, download SouadLoveGame-debug-APK from Artifacts, unzip it and install app-debug.apk on Android. This is a debug APK.
4. In Firebase enable Authentication > Sign-in method > Anonymous, create a Cloud Firestore database, and publish the supplied firestore.rules. Both phones need internet and must use the same Firebase project.
5. Create a room on one phone and enter its 12-character code on the other. Leaving the screen does not remove a player from the room.
Firebase configuration is already present in app/src/main/assets/config.js. Firebase must be configured in its console; the ZIP cannot enable these settings automatically.
