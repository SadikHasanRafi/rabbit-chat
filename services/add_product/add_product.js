const express = require('express')
const app = express()
const port = 3003

app.get('/', (req, res) => {
  res.send('add product!')
})

app.listen(port, () => {
  console.log(`add product running on port ${port}`)
})
