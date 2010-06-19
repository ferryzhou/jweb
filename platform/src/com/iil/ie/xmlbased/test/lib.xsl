<?xml version="1.0" encoding="gb2312"?>

<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:template name="getAnchors">
    <xsl:for-each select=".//a">
    <a>
    <xsl:attribute name="href"><xsl:value-of select="@href"/></xsl:attribute>
    <xsl:value-of select="."/>
    </a>
    </xsl:for-each>
  </xsl:template>
</xsl:stylesheet>