import React from 'react';
import 'styles/index.css';
import ReactDOM from 'react-dom/client';
import { App } from 'components/app/App';
import reportWebVitals from 'config/reportWebVitals';

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);
root.render(
  // <React.StrictMode>
    <App />
  // </React.StrictMode>
);

reportWebVitals();
