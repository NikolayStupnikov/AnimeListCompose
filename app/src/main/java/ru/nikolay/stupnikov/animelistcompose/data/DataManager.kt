package ru.nikolay.stupnikov.animelistcompose.data

import ru.nikolay.stupnikov.animelistcompose.data.repository.DatabaseRepository
import ru.nikolay.stupnikov.animelistcompose.data.repository.NetworkRepository

interface DataManager: NetworkRepository, DatabaseRepository