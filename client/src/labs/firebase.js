import { initializeApp } from 'firebase/app';
import { getMessaging } from 'firebase/messaging';
import { getPermission } from 'firebase/getPermission';

const firebaseConfig = {
  apiKey: 'AIzaSyA6g7NRQJC9M_nlfiy-FqK6BHi5DkzVnKY',
  authDomain: 'payment-a6511.firebaseapp.com',
  projectId: 'payment-a6511',
  storageBucket: 'payment-a6511.appspot.com',
  messagingSenderId: '778531041477',
  appId: '1:778531041477:web:65d82c8b2ad8f8814940ac',
  measurementId: 'G-TE1Z6FT7YL',
};

initializeApp(firebaseConfig);

const messaging = getMessaging();

export { messaging };
