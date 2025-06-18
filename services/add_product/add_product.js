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

// const handleMessage = (message) => console.log("🚀 ~ handleMessage ~ message:", message);

// if (message) {
//   console.log(`Received message from ${message}: ${message.content.toString()}`);
//   const parsedMessage = JSON.parse(message.content.toString());

//   if (queue === "add-product") {
//     console.log("Handling add-product:", parsedMessage);
//   } else {
//     console.log("Unknown queue:", message);
//   }

//   channel.ack(message);
// }

// app.get("/add-product", async (req, res) => {
//   const connection = await amqp.connect("amqp://localhost");
//   channel = await connection.createChannel();

//   await channel.assertQueue("add-product", { durable: true });
//   await channel.consume(
//     "add-product",
//     (msg) => {
//       console.log("kajshdkajshdkj")
//       if (msg !== null) {
//         console.log("📥 Received:", msg.content.toString());
//         channel.ack(msg); // acknowledge the message
//         console.log("✅ Acknowledged");
//       } else {
//         console.log("❌ Received null message");
//       }
//     },
//     {
//       noAck: false, 
//     }
//   );



//   setTimeout(() => {
//     res.send("result");
//   }, 100);
// });


app.get("/add-product", async (req, res) => {
  const connection = await amqp.connect("amqp://localhost");
  const channel = await connection.createChannel();

  await channel.assertQueue("add-product", { durable: true });

  await channel.consume(
    "add-product",
    (msg) => {
      if (msg !== null) {
        const message = msg.content.toString();
        console.log("📥 Received:", message);
        channel.ack(msg);
        res.send(`✅ Received and acknowledged: ${message}`);
      } else {
        res.send("❌ No message received");
      }
    },
    { noAck: false }
  );

  const x = await channel.get("add-product")
  console.log("🚀 ~ add_product.js:93 ~ app.get ~ x:", x)
  res.send(await channel.get("add-product"))
});


app.listen(port, () => {
  console.log(`add product running on port ${port}`);
});

// app.post('/add-product',async (req, res) => {

//   const doc = req.body;
//   console.log("🚀 ~ add_product.js:28 ~ app.post ~ doc:", JSON.stringify(doc))
//   const result = await product_collection.insertOne(doc);
//   console.log(`A product was inserted with the _id: ${result.insertedId}`);

//   res.send(result)
// })
