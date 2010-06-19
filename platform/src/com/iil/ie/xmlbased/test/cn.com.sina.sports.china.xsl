<?xml version="1.0" encoding="gb2312"?>

<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:import href="lib.xsl" />

<xsl:template match="/">
  <root>
  <xsl:apply-templates/>
  </root>
</xsl:template>

<xsl:template match="html[1]/body[1]/table[2]/tr[1]/td[3]/ul[1]">
<jiaanewslist>
  <xsl:call-template name="getAnchors"/>
</jiaanewslist>
</xsl:template>

</xsl:stylesheet>