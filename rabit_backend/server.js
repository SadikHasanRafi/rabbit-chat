// const { default: axios } = require('axios')
const { addProduct } = require('./publisher')
const express = require('express')


const app = express()
const port = 8080
app.use(express.json());

app.get('/',async (req, res) => {
  res.send('Hello from rabbit mq 🐇!')
})


app.post('/add-product', async (req,res)=>{
  console.log("🚀 ~ server.js:19 ~ app.post ~ req.body:", req.body)
  await addProduct("add-product",JSON.stringify(req.body))

  res.send('queue is added')
})


app.listen(port, () => {
  console.log(`server listening on port ${port}`)
})
