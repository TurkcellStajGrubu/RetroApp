#  Retro Meeting
Retro Meeting, kullanıcıların kayıt olabileceği ve giriş yapabileceği bir Android uygulamasıdır. Kullanıcılar, kayıtlı notları görüntüleyebilir, silebilir, notları filtreleyebilir, arayabilir ve yeni notlar ekleyebilirler. Ayrıca, kullanıcılar retro toplantıları düzenleyebilir ve toplantı süresince anonim mesajlar bırakabilirler.

## Uygulamanın Barındırdığı Özellikler
- Kullanıcılar uygulamaya kayıt olabilir ve giriş yapabilirler.
- Şifre yenileme işlemi için kullanıcılar şifre yenileme seçeneğini kullanabilirler.
- Home sayfasında kullanıcının daha önceden kayıt ettiği notlar en son eklenen not en üstte olacak şekilde görüntülenir.
- Kullanıcılar notları arayabilir ve türe göre filtreleyebilir.
- Floating action button ile kullanıcılar yeni not ekleme sayfasına gidebilir ve yeni notlar, resimler ve URL adresleri ekleyebilirler.
- Notların üzerine tıklayarak notların detaylarını görebilir ve düzenleyebilirler.
- Uzun süreli tıklama işlemi ile kullanıcılar notları silebilirler.
- Retro sayfasında kullanıcılar retro toplantıları oluşturabilir, başlıklarını ve sürelerini belirleyebilirler.
- Retro toplantısını oluşturan kullanıcı, toplantı süresince toplantı süresini değiştirebilir ve toplantıyı sonlandırabilir.
- Toplantıya katılan kullanıcılar, iyi giden veya geliştirilmesi gereken türlerinden birini seçerek anonim mesajlar bırakabilirler.
- Retro toplantısı sona erdiğinde, katılımcılar toplantı sayfasından çıkarılır ve toplantıyı oluşturan kullanıcı bırakılan mesajları okuyabilir.
- Admin kullanıcı, bırakılan mesajları görürken, mesaj atan kullanıcının adını görmez ve sadece mesajları okur.
- Admin, istediği mesajları silebilir ve mesajları kaydedebilir.
- Kaydedilen mesajlar, home sayfasında notlar arasında görüntülenir.


## Kütüphaneler ve Özellikler
- Compose: Android'in modern kullanıcı arayüzü geliştirme kitidir.
- Firebase Auth: Kimlik doğrulama.
- Firebase Firestore: Gerçek zamanlı veritabanı.
- Firebase Storage: Dosya depolama.
- Firebase Forgot Password: Şifre yenileme.
- AndroidX Kütüphaneleri: Android geliştirmek için çeşitli kütüphaneleri içerir.
- Hilt: Bağımlılık enjeksiyon çerçevesi.
- Coil-Compose: Resim işleme ve gösterme kütüphanesi.
- WorkManager: Zamanlanmış ve gecikmeli görevler için arka plan görev yöneticisi.
- Kotlin Coroutines: Asenkron işlemleri kolaylaştıran hafif çerçeve.
- Glide: Resimleri hızlı ve etkili bir şekilde yükleme ve gösterme kütüphanesi.

## Kurulum ve Kullanım
Proje, Android Studio'da açılarak çalıştırılabilir. Aşağıdaki adımları izleyerek projeyi yerel ortamınızda çalıştırabilirsiniz:

1. Bu depoyu klonlayın veya zip dosyasını indirin.
2. Android Studio'yu açın ve projeyi açın.
3. Firebase Console'da bir proje oluşturun ve google-services.json dosyasını projenize ekleyin.
4. Proje ayarlarınızı ve gereksinimleri yapılandırın.
5 .Emülatörde veya fiziksel cihazda uygulamayı çalıştırın.

## Kuallanıcı Giriş Ve Kayıt Ekran Görüntüleri
<p float="left">
  <img src=https://github.com/TurkcellStajGrubu/RetroApp/assets/139555054/2b0c9d93-510c-43a4-b43d-1ca63713968b width="28%" />
  <img src=https://github.com/TurkcellStajGrubu/RetroApp/assets/139555054/ea1e38da-a85b-43b8-96d7-8140c392c3b4 width="28%" />
  <img src=https://github.com/TurkcellStajGrubu/RetroApp/assets/139555054/07d47de9-a08b-41db-ba13-685b9fa8898f width="28%" />
</p>

## HomeScreen ve Not Ekleme Ekran Görüntüleri
<p float="left">
  <img src=https://github.com/TurkcellStajGrubu/RetroApp/assets/139555054/4c8adf0c-98ea-46f2-bc8b-6e6d4548a91f width="28%" />
  <img src=https://github.com/TurkcellStajGrubu/RetroApp/assets/139555054/df3036e6-c661-4788-8c52-eab45740c872 width="28%" />
  <img src=https://github.com/TurkcellStajGrubu/RetroApp/assets/139555054/eec2108b-964f-457a-894f-7539026211c4 width="28%" />
  <img src=https://github.com/TurkcellStajGrubu/RetroApp/assets/139555054/a019deb0-9b73-4a3f-9ab2-a4f467313903 width="28%" />
  <img src=https://github.com/TurkcellStajGrubu/RetroApp/assets/139555054/00f4eb05-a1af-4cc2-8271-28fcda9f07f3 width="28%" />
  <img src=https://github.com/TurkcellStajGrubu/RetroApp/assets/139555054/511f826c-5de6-48f5-bfed-6c5a96d75d5c width="28%" />  
</p>

## RetroScreen Ekran Görüntüleri
<p float="left">
  <img src=https://github.com/TurkcellStajGrubu/RetroApp/assets/139555054/a864a349-ff92-49b2-b3c6-3b5c1fac616e width="28%" />
  <img src=https://github.com/TurkcellStajGrubu/RetroApp/assets/139555054/b16bc96b-712a-444f-acfe-379f7e8971db width="28%" />
  <img src=https://github.com/TurkcellStajGrubu/RetroApp/assets/139555054/f6e179d1-5134-4479-b6e5-bee71498c293 width="28%" />
  <img src=https://github.com/TurkcellStajGrubu/RetroApp/assets/139555054/17674436-fa2c-4cb5-bffe-f32e33dd75fe width="28%" />
  <img src=https://github.com/TurkcellStajGrubu/RetroApp/assets/139555054/6e3142b0-f405-48e5-bef1-15bf690fb163 width="28%" />
  <img src=https://github.com/TurkcellStajGrubu/RetroApp/assets/139555054/e78f675f-07cc-477f-b203-5ebce99e41c8 width="28%" />
  <img src=https://github.com/TurkcellStajGrubu/RetroApp/assets/139555054/9cf6afd5-d4f6-48f3-98a2-c58548b7c179 width="28%" />
  <img src=https://github.com/TurkcellStajGrubu/RetroApp/assets/139555054/d1c63828-9454-489e-998f-7aa37933fb2b width="28%" />
</p>








    
