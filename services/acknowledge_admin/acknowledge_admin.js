const express = require('express')
const app = express()
const port = 3001

app.get('/', (req, res) => {
  res.send('acknowledge admin!')
})


app.get('/admin-acknowledge',async (req,res)=>{
  res.send(` Admin acknowledged !!! `)
})

app.listen(port, () => {
  console.log(`acknowledge admin running on port ${port}`)
})





// const { MongoClient, ServerApiVersion } = require('mongodb');
// const uri = "mongodb+srv://sadikhasan:13255mewmewmew@cluster0.ahqjc0b.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";

// // Create a MongoClient with a MongoClientOptions object to set the Stable API version
// const client = new MongoClient(uri, {
//   serverApi: {
//     version: ServerApiVersion.v1,
//     strict: true,
//     deprecationErrors: true,
//   }
// });

// async function run() {
//   try {
//     // Connect the client to the server	(optional starting in v4.7)
//     await client.connect();
//     // Send a ping to confirm a successful connection
//     await client.db("admin").command({ ping: 1 });
//     console.log("Pinged your deployment. You successfully connected to MongoDB!");
//   } finally {
//     // Ensures that the client will close when you finish/error
//     await client.close();
//   }
// }
// run().catch(console.dir);
