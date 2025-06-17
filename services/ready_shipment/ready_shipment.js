const express = require('express')
const app = express()
const port = 3004

app.get('/', (req, res) => {
  res.send('ready shipment!')
})

app.listen(port, () => {
  console.log(`ready shipment running on port ${port}`)
})
