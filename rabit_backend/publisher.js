// import amqp from "amqplib";
const amqp = require("amqplib");

let channel;


  const addProduct = async (queue, message) => {
  console.log("🚀 ~ publisher.js:8 ~ addProduct ~ message:", message)
  console.log("🚀 ~ publisher.js:8 ~ addProduct ~ queue:", queue)

    const connection = await amqp.connect("amqp://localhost");
    channel = await connection.createChannel();

    await channel.assertQueue(queue, { durable: true }); //creating the queue
    channel.sendToQueue(queue, Buffer.from(message)); //making it binary
    console.log(`Message sent to ${queue}: ${message}`);

    channel.close();
    connection.close();
    // process.once("exit", () => {
    // });

  };



// const runProducer = async () => {



//   const sendNotification = async (queue, payload) => {
//     const message = JSON.stringify(payload);
//     await sendMessage(queue, message);
//   };


// };

module.export = { addProduct };
