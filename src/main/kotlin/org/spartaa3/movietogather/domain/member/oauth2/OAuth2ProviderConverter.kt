package org.spartaa3.movietogather.domain.member.oauth2

import org.springframework.core.convert.converter.Converter

class OAuth2ProviderConverter : Converter<String, OAuth2Provider> {

    override fun convert(source: String): OAuth2Provider {
        return runCatching {
            OAuth2Provider.valueOf(source.uppercase())
        }.getOrElse {
            throw IllegalArgumentException()
        }
    }
}