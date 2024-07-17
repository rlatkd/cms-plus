importScripts('https://www.gstatic.com/firebasejs/9.12.3/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/9.12.3/firebase-messaging.js');

const firebaseConfig = {
  apiKey: 'AIzaSyA6g7NRQJC9M_nlfiy-FqK6BHi5DkzVnKY',
  authDomain: 'payment-a6511.firebaseapp.com',
  projectId: 'payment-a6511',
  storageBucket: 'payment-a6511.appspot.com',
  messagingSenderId: '78531041477',
  appId: '1:778531041477:web:65d82c8b2ad8f8814940ac',
};

firebase.initializeApp(firebaseConfig);

const messaging = firebase.messaging();

messaging.onBackgroundMessage(payload => {
  console.log('[firebase-messaging-sw.js] Received background message ', payload);
  // Customize notification here
  const notificationTitle = payload.notification.title;
  const notificationOptions = {
    body: payload.notification.body,
  };

  self.registration.showNotification(notificationTitle, notificationOptions);
});
