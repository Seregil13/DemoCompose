package com.example.core

import com.example.core.data.Outcome

interface Paginator<Key, Item> {
    suspend fun loadNextItems()
    fun reset()
}

class DefaultPaginator<Key, Item>(
    private val initialKey: Key,
    private inline val onLoadUpdated: (Boolean) -> Unit,
    private inline val onRequest: suspend (nextKey: Key) -> Outcome<List<Item>>,
    private inline val getNextKey: suspend (List<Item>) -> Key,
    private inline val onError: suspend (Throwable?) -> Unit,
    private inline val onSuccess: suspend (items: List<Item>, newKey: Key) -> Unit
): Paginator<Key, Item> {

    private var currentKey = initialKey
    private var isMakingRequest = false

    override suspend fun loadNextItems() {
        if(isMakingRequest) {
            return
        }
        isMakingRequest = true
        onLoadUpdated(true)
        val result = onRequest(currentKey)
        isMakingRequest = false

        when (result) {
            is Outcome.Error -> {
                onError(result.cause)
                onLoadUpdated(false)
                return
            }
            is Outcome.Success -> {
                currentKey = getNextKey(result.value)
                onSuccess(result.value, currentKey)
                onLoadUpdated(false)
            }
        }
    }

    override fun reset() {
        currentKey = initialKey
    }
}
