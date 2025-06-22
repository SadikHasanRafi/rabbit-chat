const amqp = require("amqplib");
const express = require("express");
const app = express();
app.use(express.json());
const port = 3003;
const { MongoClient, ServerApiVersion } = require("mongodb");
const uri = "mongodb+srv://sadikhasan:13255mewmewmew@cluster0.ahqjc0b.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
// Create a MongoClient with a MongoClientOptions object to set the Stable API version
const client = new MongoClient(uri, {
  serverApi: {
    version: ServerApiVersion.v1,
    strict: true,
    deprecationErrors: true,
  },
});

const db = client.db("rabbit");
const product_collection = db.collection("product");

let channel;

app.get("/", (req, res) => {
  res.send("Add Product!");
});





app.get("/add-product", async (req, res) => {
  await startConsumer(); 
  res.send(`✅ Message received`);
});


let messageIndex = 1; // Initialize the message counter

async function startConsumer() {
  const connection = await amqp.connect("amqp://localhost");
  const channel = await connection.createChannel();

  await channel.assertQueue("add-product", { durable: true });

  channel.consume("add-product", (msg) => {
    if (msg !== null) {
      const message = msg.content.toString();
      console.log(`${messageIndex++} 📥  Received: ${message}`);
      channel.ack(msg);
      // Do something with the message
    }
  }, { noAck: false });

  console.log("🔁 Waiting for messages in 'add-product' queue...");
}

















app.listen(port, () => {
  console.log(`add product running on port ${port}`);
});







