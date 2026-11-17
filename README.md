# Şifre Yöneticisi (Java)

Parolaları ana parola ile şifreleyerek yerel bir dosyada saklayan, konsol tabanlı bir şifre yöneticisi. Java ile nesne tabanlı tasarım ve güvenli parola saklama pratiklerini göstermek için geliştirildi.

## Özellikler

- Kayıt ekleme, listeleme ve silme (site, kullanıcı adı, şifre)
- Rastgele güçlü parola üretici
- Tüm kayıtlar ana parolayla şifrelenmiş tek bir dosyada (`vault.dat`) saklanır
- Yanlış ana parolada kasa açılmaz

## Kullanılan güvenlik yöntemleri

| Konu | Yöntem |
|------|--------|
| Anahtar türetme | PBKDF2WithHmacSHA256, 210.000 iterasyon, rastgele salt |
| Şifreleme | AES-256-GCM (hem gizlilik hem bütünlük doğrulaması) |
| Rastgelelik | `SecureRandom` (salt, IV ve parola üretimi) |
| Parola işleme | `char[]` kullanımı, `toString` içinde şifre yazdırılmaz |
| Karşılaştırma | `MessageDigest.isEqual` ile sabit süreli karşılaştırma |

Kriptografik algoritmalar