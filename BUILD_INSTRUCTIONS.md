# تعليمات بناء وتشغيل FireNova Launcher

## المتطلبات الأساسية

1. **Android Studio** (الإصدار الأحدث)
2. **JDK 8 أو أحدث**
3. **Android SDK** مع API Level 21+
4. **جهاز Fire TV** أو محاكي Android TV

## خطوات البناء

### 1. فتح المشروع
```bash
# افتح Android Studio
# اختر "Open an existing project"
# انتقل إلى مجلد المشروع
```

### 2. إعداد Gradle
```bash
# تأكد من أن Gradle Wrapper محدث
./gradlew wrapper --gradle-version=8.0
```

### 3. بناء التطبيق
```bash
# بناء APK للتطوير
./gradlew assembleDebug

# بناء APK للإنتاج
./gradlew assembleRelease
```

## التثبيت على Fire TV

### الطريقة الأولى: ADB
```bash
# تفعيل وضع المطور على Fire TV
# Settings > My Fire TV > Developer Options > ADB Debugging

# ربط الجهاز
adb connect [IP_ADDRESS]:5555

# تثبيت التطبيق
adb install app-debug.apk
```

### الطريقة الثانية: Amazon Appstore
1. ارفع APK إلى Amazon Developer Console
2. أرسل للتقييم
3. نزل من Amazon Appstore

## إعداد Launcher الافتراضي

### على Fire TV:
1. Settings > Applications > Manage Installed Applications
2. ابحث عن "FireNova Launcher"
3. اختر "Launch by default"
4. اختر "Always"

## اختبار التطبيق

### الميزات المطلوب اختبارها:
- [ ] تحميل قائمة التطبيقات
- [ ] عرض التطبيقات المحملة جانبياً (Sideloaded)
- [ ] التنقل بالريموت (D-Pad)
- [ ] تأثيرات التركيز (Focus Effects)
- [ ] تشغيل التطبيقات
- [ ] معالجة الأخطاء

### اختبار الأداء:
- [ ] سرعة التحميل الأولي
- [ ] سرعة تحميل الأيقونات
- [ ] استهلاك الذاكرة
- [ ] استقرار التطبيق

## استكشاف الأخطاء

### مشاكل شائعة:

#### 1. التطبيق لا يظهر التطبيقات المحملة جانبياً
**الحل:** تأكد من إضافة `QUERY_ALL_PACKAGES` permission في AndroidManifest.xml

#### 2. مشاكل في التنقل بالريموت
**الحل:** تأكد من استخدام `TvLazyVerticalGrid` وليس `LazyVerticalGrid`

#### 3. بطء في تحميل الأيقونات
**الحل:** تأكد من استخدام Coil بشكل صحيح في AppCard

#### 4. التطبيق لا يفتح
**الحل:** تأكد من إضافة `LEANBACK_LAUNCHER` category في AndroidManifest.xml

## تحسين الأداء

### نصائح للأداء الأمثل:
1. **تجنب تحميل الأيقونات في Repository**
2. **استخدم Coil للتحميل الكسول**
3. **شغل PackageManager queries في الخلفية**
4. **استخدم StateFlow لإدارة الحالة**

### مراقبة الأداء:
```bash
# مراقبة استهلاك الذاكرة
adb shell dumpsys meminfo com.firenova.launcher

# مراقبة الأداء
adb shell dumpsys gfxinfo com.firenova.launcher
```

## التطوير المستقبلي

### ميزات مقترحة:
- [ ] تصنيف التطبيقات (Categories)
- [ ] البحث في التطبيقات
- [ ] تخصيص الترتيب
- [ ] إخفاء التطبيقات
- [ ] إعدادات متقدمة

### تحسينات الأداء:
- [ ] تخزين مؤقت للأيقونات
- [ ] تحميل تدريجي للتطبيقات
- [ ] تحسين استهلاك البطارية

## الدعم والمساعدة

للحصول على المساعدة:
1. راجع ملف README.md
2. تحقق من الأخطاء في Android Studio
3. استخدم ADB logs للتحقق من الأخطاء

```bash
# عرض logs التطبيق
adb logcat | grep FireNova
```
