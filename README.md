#OneBox Module on the AppEngine
The objective of this project is to provide a skeleton of a External Onebox Module provider that can be used
to integrate the Google Search appliance and the Appengine.

#What it does?
You can add documents to the full text index from AppEngine and search those documents from a OneBoxmodule 
from a GSA.

#How can I use it?
The best option is you generate the code, compile your version and upload it to the appengine.

Once you've uploaded the app, you can configure the GSA to support the External OneBox Module, you can use OneBoxAppEngine.xml as inspiration.

#Can I develop it, change it , break it?
Yes, sure, make your branch!! That's the best part of github.

Use it with Maven:

$mvn appengine:devserver

Then point with your browser to the default port:

http://localhost:8888/index.html

And add documents using the "Add Document" link

To test it locally I recommend to view only the xml, or if you have a Development GSA use it!!!

#TODO:
-Develop security:LDAP, SSO, Cookie, basic Auth
-Test integration with Predict API, HBase, BigQuery