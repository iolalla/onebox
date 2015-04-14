<xsl:stylesheet version="1.0"
 xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
 <xsl:output omit-xml-declaration="yes" indent="yes"/>
  <xsl:template match="OneBoxResults">
  <html>
  <body>
    <xsl:call-template name="onebox_results"/>
  </body>
  </html>
  </xsl:template>


     <xsl:template name="onebox_results">
	    <table border="0" cellpadding="1" cellspacing="0">
	          <tbody>
	            <tr>
	              <td rowspan="2" valign="top">
	                <img width="40" height="40">
	                  <xsl:attribute name="src"><xsl:value-of select="IMAGE_SOURCE"/></xsl:attribute>
	                </img>
	              </td>
	              <td>
	                <a>
	                  <xsl:attribute name="href"><xsl:value-of select="title/urlLink"/></xsl:attribute>
	                  <b><xsl:value-of select="title/urlText"/></b>
	                </a>
	              </td>
	            </tr>
	            <tr>
	              <td>
	                <table>
	                <xsl:for-each select="MODULE_RESULT">
	                  <tr>
	                    <td width="200" valign="top">
	                      <a>
	                          <xsl:attribute name="href"><xsl:value-of select="U"/></xsl:attribute>
	                          <font size="-1"><b><xsl:value-of select="Title"/></b></font>
	                      </a><br/>
	                      <b>
	                        Tags: <xsl:value-of select="Field[@name='tags']"/>
	                      </b>
	                    </td>
	                  </tr>
	                </xsl:for-each>
	                </table>
	              </td>
	            </tr>
	          </tbody>
	        </table>
     </xsl:template>
</xsl:stylesheet>

