const express = require('express')
const app = express()
const port = 3006

app.get('/', (req, res) => {
  res.send('update product!!')
})

app.listen(port, () => {
  console.log(`update product running on port ${port}`)
})
