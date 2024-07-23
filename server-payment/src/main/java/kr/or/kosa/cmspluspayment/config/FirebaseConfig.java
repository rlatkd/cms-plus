//package kr.or.kosa.cmspluspayment.config;
//
//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.firebase.FirebaseApp;
//import com.google.firebase.FirebaseOptions;
//import com.google.firebase.messaging.FirebaseMessaging;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.util.ResourceUtils;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//
//@Configuration
//public class FirebaseConfig {
//
//    @Value("${fcm.adminKeyPath}")
//    private String fcmAdminKeyPath;
//
//    @Bean
//    public FirebaseApp firebaseApp() throws IOException {
//        FileInputStream fis = new FileInputStream(String.valueOf(ResourceUtils.getFile(fcmAdminKeyPath)));
//
//        FirebaseOptions options = FirebaseOptions.builder()
//                .setCredentials(GoogleCredentials.fromStream(fis))
//                .build();
//        return FirebaseApp.initializeApp(options);
//    }
//
//    @Bean
//    public FirebaseMessaging firebaseMessaging(FirebaseApp firebaseApp) {
//        return FirebaseMessaging.getInstance(firebaseApp);
//    }
//
//}
