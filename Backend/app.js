// Imports
const express = require('express');
const bodyParser = require('body-parser');
const app = express();
const server = require('http').createServer(app);
const db = require('./queries');
const multer= require('multer');
const fs = require('fs');
const upload=multer({dest:'./images'});
const Crypto = require('crypto-js');
const PORT = 3005;


//Use this to parse the received json to req.body!
app.use(bodyParser.json())

//Test point
app.get('/', (req, res) => res.send('Hallo ich bin carchain-backend!'));

//Register function Parameter user/pwd --> save to db and send token?
app.put('/register',async (req, res) => {
    console.log('received request on PUT /register');
    if(!req.body|!req.body.user|!req.body.pwd){
        res.status(400).send({success: 1, error: true, errorMsg: 'Request did not contain parameters'});
    }
    else{
        try {
            //Get all users from database
            let user= req.body.user;
            let password= req.body.pwd;
            let query= 'SELECT * FROM "users"';
            var result=await db.pool.query(query);
            var dbuser="";
            //search the user in database
            result.rows.forEach(element => {
            if(element.name==user&&element.password==password)
            {
                console.log("User and password found");
                dbuser= element;
            }
            }
            );
            if(dbuser!="")
            {
                //set the token for the user in database
                console.log("DBUSER: "+dbuser.id)
                var token= Crypto.SHA256(dbuser.id+dbuser.name+dbuser.password).toString();
                let inserttoken= `UPDATE "users" SET token='${token}' WHERE id='${dbuser.id}';`;
                console.log(inserttoken);
                var ret= await db.pool.query(inserttoken);
                if(ret)
                {
                    res.status(200).send({success: 1, error: false, msg: `User registrated`, Token: token});   
                }
            }
            else
            {
                res.status(200).send({success: 1, error: false, msg: 'Message received- User and password not found'});   
            }            
        } catch (error) {
         console.log(error);   
         res.status(200).send({success: 0, error: true, msg: 'Message received'});   
        }
    }
});

//add a car with id to database
app.put('/addCar',async (req, res) => {
    console.log('received request on PUT /addCar');
    var cardid= req.body.carid;
    //check if the message contains all parameter
    if(!req.body||!cardid){
        res.status(400).send({success: 1, error: true, errorMsg: 'Request did not contain parameters'});
    }
    else{
        //insert the car to database
        var query= `INSERT INTO "cars"("externid") VALUES ('${carid}');`;
        var errormsg="";
        db.pool.query(query, (error, results) => {
            if (error) {
                console.log(error);              
            }
            else{
                console.log(results.rows);
            }            
          })
        if(errormsg!="")
        {
            res.status(200).send({success: 1, error: false, msg: 'Message received'});
        }
        else
        {
            res.status(200).send({success: 0, error: true, msg: 'Message received-insert not succeed'});
        }
        
    }
});

//add a image to a car
app.put('/addImage',upload.single('carimage'), async (req, res) => {
    console.log('received request on PUT /addImage');
    //convert image to base 64 text
    var base64image= base64_encode(req.file.path);
    //extract car id from message
    var cardid= req.body.carid;
    console.log(cardid);
    if(!req.body||!cardid){
        res.status(400).send({success: 1, error: true, errorMsg: 'Request did not contain parameters'});
    }
    else{
        //Get car by id 
        var query= `SELECT * FROM  "cars" where externid='${cardid}';`;
        var databasecarid= 0; 
        try {
            var result=await db.pool.query(query);
            console.log("Result:"+ result);
            //car not found 
            if(!result)
            {
                res.status(200).send({success: 0, error: true, msg: 'Message received-error while processing request'});
            }
            else
            {
                //insert the image to the car with the internal carid (not from user sended id)
                console.log(result);
                databasecarid=result.rows[0].id;
                if(databasecarid)
                {
                    var insertquery= `INSERT INTO "images"(carid, "imageBase64") VALUES ('${databasecarid}', '${base64image}') RETURNING id;`;
                    let insertedid= await db.pool.query(insertquery);                
                    if(insertedid)
                    {
                        res.status(200).send({success: 0, error: true, msg: 'Message received-insert succeed', imageid: insertedid.rows[0].id});
                    }                    
                }
                else
                {
                    res.status(200).send({success: 0, error: true, msg: 'Message received-insert failed'});
                }        
            }
        } catch (error) {
            res.status(200).send({success: 0, error: true, msg: 'Message received-insert failed. Maybe Carid is not in database?'});
        }
        
    }
});

function base64_encode(file) {
    // read binary data
    var bitmap = fs.readFileSync(file);
    // convert binary data to base64 encoded string
    return new Buffer(bitmap).toString('base64');
}

//get an image by id 
app.get('/getImage/:imageId',async (req, res) => {
    console.log('received request on GET /getImage');
    const imageId = req.params.imageId;
    if(!imageId){
        res.status(400).send({success: 1, error: true, errorMsg: 'Request did not contain parameters'});
    }
    else{
        try
        {
            var query= `SELECT * FROM  "images" where id='${imageId}';`;
            let result= await db.pool.query(query);   
            res.status(200).send({success: 1, error: false, msg: 'Message received', image: result.rows[0].imageBase64});
        }
        catch(error)
        {
            res.status(200).send({success: 0, error: true, msg: 'Message received-insert failed. Maybe image id is invalid?'});
        }
    }
});

//get all images for an car
app.get('/getImages/:carid',async (req, res) => {
    console.log('received request on GET /getImages');
    const carId = req.params.carid;
    if(!carId){
        res.status(400).send({success: 0, error: true, errorMsg: 'Request did not contain parameters'});
    }
    else{
        var query= `SELECT * FROM  "images" where carid=(SELECT id FROM "cars" where externid='${carId}');`;
        let result= await db.pool.query(query); 
        var jsontext= [];
        result.rows.forEach(element => {
            myobject= {"id": element.id, "image": element.imageBase64};
            jsontext.push(myobject);
            
        });          
        res.status(200).send({success: 1, error: false, msg: 'Message received with id:'+carId, imagejson:JSON.stringify (jsontext)});
    }
});

//delete a car
app.delete('/deleteCar/:carid', async (req,res) => {
    console.log("delete Car");
    console.log(req.body);
    const carid = req.params.carid;
    if (carid)
    {
        var query= `DELETE FROM  "cars" where externid='${carid}';`;
        console.log(query);
        let result= await db.pool.query(query); 
        if(result)
        {
            res.status(200).send({success: 1, error: false, msg: 'Car and all images deleted'});
        }
        else
        {
            res.status(400).send({success: 0, error: true, errorMsg: 'Failed to delete car'});
        }
    }
    else
    {
        res.status(400).send({success: 0, error: true, errorMsg: 'Request did not contain parameters'});
    }


});

//start the server 
server.listen(PORT, () => {
    console.log(`Server started at ${PORT}`);
});