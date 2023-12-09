package com.maxim.simpleschedule.core.presentation

interface Navigation {
    interface Update: AbstractCommunication.Update<Screen>
    interface Observe: AbstractCommunication.Observe<Screen>
    interface Mutable: Update, Observe
    class Base: Mutable, AbstractCommunication.Abstract<Screen>(SingleLiveEvent())
}