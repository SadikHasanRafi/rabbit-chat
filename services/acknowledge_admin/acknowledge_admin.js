const express = require('express')
const app = express()
const port = 3001

app.get('/', (req, res) => {
  res.send('acknowledge admin!')
})

app.listen(port, () => {
  console.log(`acknowledge admin running on port ${port}`)
})
