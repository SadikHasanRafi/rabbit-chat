const express = require('express')
const app = express()
const port = 3002

app.get('/', (req, res) => {
  res.send('acknowledge buyer running ')
})

app.listen(port, () => {
  console.log(`acknowledge buyer running  on port ${port}`)
})
