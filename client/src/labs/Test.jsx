// Test.jsx
import { useEffect } from 'react';
import { initializeApp } from 'firebase/app';
import { getMessaging, getToken, onMessage } from 'firebase/messaging';

const firebaseConfig = {
  apiKey: 'AIzaSyA6g7NRQJC9M_nlfiy-FqK6BHi5DkzVnKY',
  authDomain: 'payment-a6511.firebaseapp.com',
  databaseURL: 'https://imap-push-server.firebaseio.com',
  projectId: 'payment-a6511',
  storageBucket: 'payment-a6511.appspot.com',
  messagingSenderId: '78531041477',
  appId: '1:778531041477:web:65d82c8b2ad8f8814940ac',
};

const app = initializeApp(firebaseConfig);
const messaging = getMessaging(app);

const requestPermissionAndGetToken = async () => {
  try {
    const permission = await Notification.requestPermission();
    if (permission === 'granted') {
      console.log('Notification permission granted.');

      // 서비스 워커 등록
      const registration = await navigator.serviceWorker.register('/firebase-messaging-sw.js');
      const token = await getToken(messaging, {
        vapidKey: 'YOUR_VAPID_KEY',
        serviceWorkerRegistration: registration,
      });
      console.log('FCM Token:', token);
    } else {
      console.log('Unable to get permission to notify.');
    }
  } catch (error) {
    console.error('Error getting permission or token:', error);
  }
};

const Test = () => {
  useEffect(() => {
    requestPermissionAndGetToken();

    onMessage(messaging, payload => {
      console.log('Message received. ', payload);
      // Customize notification here
      console.log('Title: ', payload.notification.title);
      console.log('Body: ', payload.notification.body);
    });
  }, []);

  return (
    <>
      <h1>test</h1>
    </>
  );
};

export default Test;
