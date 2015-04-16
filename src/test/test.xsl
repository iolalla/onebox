<xsl:stylesheet version="1.0"
 xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
 <xsl:output omit-xml-declaration="yes" indent="yes"/>
  <xsl:template match="OneBoxResults">
  <html>
  <body>
    <xsl:call-template name="appEngineOneBoxResults"/>
  </body>
  </html>
  </xsl:template>

  <xsl:template name="appEngineOneBoxResults">
    <table border="0" cellpadding="1" cellspacing="0">
          <tbody>
            <tr>
              <td valign="top">
                <a>
                  <xsl:attribute name="href"><xsl:value-of select="title/urlLink"/></xsl:attribute>
                <img>
                  <xsl:attribute name="src"><xsl:value-of select="IMAGE_SOURCE"/></xsl:attribute>
                </img>
                </a>
              </td>
                <xsl:for-each select="MODULE_RESULT">
                    <td width="80" valign="top">
                      <a>
                          <xsl:attribute name="href"><xsl:value-of select="U"/></xsl:attribute>
						  <xsl:attribute name="alternate"><xsl:value-of select="title"/></xsl:attribute>
						  <xsl:attribute name="target">_blank</xsl:attribute>
        	                <img>
        						<xsl:attribute name="src"><xsl:value-of select="Field[@name='image']"/></xsl:attribute>
							</img>
                      </a>
					  <!-- 
					  <br/>
                        Tags: <xsl:value-of select="Field[@name='tags']"/>
					  -->
                    </td>
                </xsl:for-each>
            </tr>
          </tbody>
        </table>
  </xsl:template>

</xsl:stylesheet>

