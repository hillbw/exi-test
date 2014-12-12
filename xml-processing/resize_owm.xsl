<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" indent="yes" encoding="utf-8" />
        
    <xsl:param name="i"/>
    
    <xsl:template match="/">
        <xsl:copy>
            <xsl:copy-of select="@*" />
            <xsl:apply-templates/>
        </xsl:copy>
    </xsl:template>
    
    <xsl:template match="*">
        <xsl:copy>
            <xsl:copy-of select="@*" />
            <xsl:apply-templates />
        </xsl:copy>
    </xsl:template>

    <xsl:template match="group">
        <xsl:copy>
            <xsl:copy-of select="@*" />
            <xsl:apply-templates select="current[position() &lt;= $i]"/>
        </xsl:copy>
    </xsl:template>

    <xsl:template match="current">
        <xsl:copy>
            <xsl:copy-of select="@*" />
            <xsl:apply-templates/>
        </xsl:copy>
    </xsl:template>
   
</xsl:stylesheet>  

    