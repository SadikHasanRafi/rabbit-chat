const express = require('express')
const app = express()
const port = 3005

app.get('/', (req, res) => {
  res.send('update balance!')
})

app.listen(port, () => {
  console.log(`update balance running on port ${port}`)
})
