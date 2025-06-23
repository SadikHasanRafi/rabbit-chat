const express = require('express');
const axios = require('axios');

const app = express();
const PORT = 3004;

const INTERVAL_MS = 1300; // 2 seconds
const DURATION_MS = 10 * 60 * 1000; // 10 minutes
let elapsed = 0;

app.get('/', (req, res) => {
  res.send('update product!!');

  const interval = setInterval(() => {
    // First call to direct exchange
    axios.post('http://localhost:8090/send-message-by-admin-007', {
      exchange: 'direct',
      message: 'successfully direct working with route key',
    })
    .then(response => {
      console.log('✅ Direct message sent');
      console.log('📥 Direct response:', response.data);
    })
    .catch(err => {
      console.error('❌ Error sending to direct:', err.message);
      if (err.response) {
        console.error('📥 Direct error response:', err.response.data);
      }
    });

    // Second call to fanout exchange
    axios.post('http://localhost:8090/send-message-by-admin', {
      exchange: 'fanout',
      message: 'successfully fanout working',
    })
    .then(response => {
      console.log('✅ Fanout message sent');
      console.log('📥 Fanout response:', response.data);
    })
    .catch(err => {
      console.error('❌ Error sending to fanout:', err.message);
      if (err.response) {
        console.error('📥 Fanout error response:', err.response.data);
      }
    });

    // update timer
    elapsed += INTERVAL_MS;
    if (elapsed >= DURATION_MS) {
      clearInterval(interval);
      console.log('⏹️ Stopped after 10 minutes');
    }

  }, INTERVAL_MS);s
});

app.listen(PORT, () => {
  console.log(`🚀 Server running at http://localhost:${PORT}`);
});
