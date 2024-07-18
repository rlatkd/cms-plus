if (typeof importScripts === 'function') {
  importScripts('https://www.gstatic.com/firebasejs/8.10.1/firebase-app.js');
  importScripts('https://www.gstatic.com/firebasejs/8.10.1/firebase-messaging.js');

  const firebaseApp = firebase.initializeApp({
    apiKey: 'AIzaSyA6g7NRQJC9M_nlfiy-FqK6BHi5DkzVnKY',
    authDomain: 'payment-a6511.firebaseapp.com',
    projectId: 'payment-a6511',
    storageBucket: 'payment-a6511.appspot.com',
    messagingSenderId: '778531041477',
    appId: '1:778531041477:web:65d82c8b2ad8f8814940ac',
    measurementId: 'G-TE1Z6FT7YL',
  });

  const messaging = firebase.messaging(firebaseApp);

  self.addEventListener('install', () => {
    console.log('[파이어베이스 메시징 SW 로드]');
  });

  messaging.onBackgroundMessage(payload => {
    console.log('[firebase-messaging-sw.js] Received background message ', payload);
    const notificationTitle = payload.notification.title;
    const notificationOptions = {
      body: payload.notification.body,
      // icon: '/firebase-logo.png',
    };

    self.registration.showNotification(notificationTitle, notificationOptions);
  });
}
