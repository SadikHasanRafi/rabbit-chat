const express = require('express');
const axios = require('axios');

const app = express();
const PORT = 3004;
app.use(express.text()); // 🔥 This is what makes text/plain work
app.use(express.json()); // For parsing application/json
const INTERVAL_MS = 10500; // 2 seconds
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


// http://localhost:5000/send-message
let automateIntervalId = null;
let automateCountdownId = null;

app.post("/automate-producer", (req, res) => {
  const { frequencySeconds, loremSize } = req.body;

  console.log("⚙️ Received automation config:", req.body);

  const freq = parseFloat(frequencySeconds);
  const size = parseFloat(loremSize);

  if (isNaN(freq) || isNaN(size) || freq <= 0) {
    return res.status(400).send("Invalid frequencySeconds or loremSize");
  }

  const intervalMs = freq * 1000;
  console.log(`🕒 Sending ${size} every ${intervalMs}ms`);

  let nextSendIn = intervalMs;

  // Clear any previous intervals if running
  if (automateIntervalId) clearInterval(automateIntervalId);
  if (automateCountdownId) clearInterval(automateCountdownId);

  // ⏱️ Countdown timer
  automateCountdownId = setInterval(() => {
    nextSendIn -= 100;
    const secondsLeft = (nextSendIn / 1000).toFixed(2);
    process.stdout.write(`⏳ Next send in: ${secondsLeft}s\r`);
    if (nextSendIn <= 0) nextSendIn = intervalMs;
  }, 1);

  // 🔁 Send message every intervalMs
  automateIntervalId = setInterval(async () => {
    try {
      const response = await axios.post("http://localhost:5000/send-message", size.toString(), {
        headers: { "Content-Type": "text/plain" },
      });

      console.log(`\n📤 Sent: ${size} → ${response.data}`);
    } catch (err) {
      console.error("\n❌ Failed to send:", err.message);
      clearInterval(automateIntervalId);
      clearInterval(automateCountdownId);
      automateIntervalId = null;
      automateCountdownId = null;
    }
  }, intervalMs);

  res.send({
    status: "running",
    frequencySeconds: freq,
    sendingValue: size,
  });
});

// API to stop the automated producer
app.get("/stop-automate-producer", (req, res) => {
  if (automateIntervalId) {
    clearInterval(automateIntervalId);
    automateIntervalId = null;
  }
  if (automateCountdownId) {
    clearInterval(automateCountdownId);
    automateCountdownId = null;
  }
  console.log("🛑 Automation stopped");
  res.send({ status: "stopped" });
});




app.listen(PORT, () => {
  console.log(`🚀 Server running at http://localhost:${PORT}`);
});
