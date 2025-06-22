// const { default: axios } = require('axios')
// const { addProduct } = require('./publisher')
const express = require('express')
const amqp = require("amqplib");


const app = express()
const port = 8000
app.use(express.json());
let channel;
// Add these lines
const bodyParser = require('body-parser');
app.use(bodyParser.json()); // For JSON bodies
app.use(bodyParser.urlencoded({ extended: true })); // For form-encoded bodies



app.get('/',async (req, res) => {
  res.send('Hello from rabbit mq 🐇!')
})




app.post('/add-product', async (req, res) => {
  const connection = await amqp.connect("amqp://localhost");
  const channel = await connection.createChannel();
  let queue = "add-product";

  await channel.assertQueue(queue, { durable: true }); // ⚠️ Important point here

  channel.sendToQueue(queue, Buffer.from(JSON.stringify(req.body)));
  console.log(`message sent to ${queue}:`, req.body);

  await channel.close();
  await connection.close();

  res.send('Queue is added');
});







app.listen(port, () => {
  console.log(`server listening on port ${port}`)
})














  // const addProduct = async (queue, message) => {
  // console.log("🚀 ~ publisher.js:8 ~ addProduct ~ message:", message)
  // console.log("🚀 ~ publisher.js:8 ~ addProduct ~ queue:", queue)

  //   const connection = await amqp.connect("amqp://localhost");
  //   channel = await connection.createChannel();

  //   await channel.assertQueue(queue, { durable: true }); //creating the queue
  //   channel.sendToQueue(queue, Buffer.from(message)); //making it binary
  //   console.log(`Message sent to ${queue}: ${message}`);

  //   channel.close();
  //   connection.close();
  

  // };