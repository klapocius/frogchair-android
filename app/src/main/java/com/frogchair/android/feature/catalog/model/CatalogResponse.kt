package com.frogchair.android.feature.catalog.model

import com.frogchair.android.feature.catalog.data.RestCatalogResponse

class CatalogResponse(
    val response: RestCatalogResponse? = null,
    val error: String? = null
)