const express = require('express')
const app = express()
const port = 3000

app.get('/', (req, res) => {
  res.send('Hello server!')
})

app.listen(port, () => {
  console.log(`server listeening on port ${port}`)
})
