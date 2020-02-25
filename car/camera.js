const PiCamera = require('pi-camera');
const QRReader = require('qrcode-reader');
const fs = require('fs');
const jimp = require('jimp');
const myCamera = new PiCamera({
  mode: 'photo',
  output: `${ __dirname }/test.jpg`,
  width: 640,
  height: 480,
  nopreview: true,
});

setInterval(function() {
  myCamera.snap()
    .then((result) => {
      // Your picture was captured
      run().catch(error => console.error(error.stack));

      async function run() {
        const img = await jimp.read(fs.readFileSync('./test.jpg'));
        const qr = new QRReader();

        // qrcode-reader's API doesn't support promises, so wrap it
        const value = await new Promise((resolve, reject) => {
          qr.callback = (err, v) => err != null ? reject(err) : resolve(v);
          qr.decode(img.bitmap);
        });

      // { result: 'http://asyncawait.net',
      //   points:
      //     [ FinderPattern {
      //         x: 68.5,
      //         y: 252,
      //         count: 10,
      // ...
      console.log(value);

      // http://asyncawait.net
      console.log(value.result);
      }
    })
  .catch((error) => {
    // Handle your error
  });
}, 15000);
