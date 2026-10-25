Souad Love Game — Firebase Android project

هذا مشروع Android Studio وليس APK مبنياً. لا يمكن تشغيل اللعب والدردشة قبل إعداد Firebase.

1) ادخل إلى https://console.firebase.google.com وأنشئ مشروعاً.
2) في Authentication > Sign-in method فعّل Anonymous.
3) في Firestore Database أنشئ قاعدة بيانات في وضع Production.
4) في Firestore > Rules انسخ محتوى firestore.rules وانشر القواعد.
5) أضف Web App إلى مشروع Firebase من Project settings، وانسخ قيم firebaseConfig إلى:
   app/src/main/assets/config.js
   (apiKey, authDomain, projectId, appId).
6) افتح المجلد في Android Studio. يحتاج الإنترنت لتنزيل Firebase JavaScript SDK من gstatic.
7) Build > Build APK(s). ملف APK يوجد غالباً في app/build/outputs/apk/debug/.
8) ثبّت التطبيق على هاتفين. أنشئ غرفة على الهاتف الأول وأدخل كود الغرفة على الثاني.

تنبيه أمني: القواعد تتحقق من عضوية الغرفة للرسائل والكتابة، لكنها لا تتحقق من صحة كل حركة لعبة أو نتيجة فوز. لأن منطق الألعاب يعمل على الأجهزة، يستطيع عميل معدل التلاعب بالنتيجة. للاستعمال التنافسي الحقيقي، انقل التحقق من الحركات إلى Cloud Functions أو خادم موثوق.

تنبيه: دخول Anonymous قد يفقد إمكانية الرجوع إلى الغرفة عند مسح بيانات التطبيق أو تبديل الجهاز. الرسائل تُحفظ في Firestore. لم تُختبر نسخة APK على جهاز فعلي.

تم ربط config.js بمشروع souadlovegame المرسل من المستخدم.
انشر firestore.rules من ملف المشروع في Firebase Console > Firestore > Rules > Publish.
لا تنشر قواعد عامة مثل allow read, write: if true.
ملاحظة: قواعد الغرف تسمح للعضوين بتعديل نتائج اللعب ولا تتحقق من صحة كل حركة.
