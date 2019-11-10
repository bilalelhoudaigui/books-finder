# Books Finder

A simple search engine for books.

## Used Technologies

* Spring v5
* Spring Boot v2.2.1
* Angular v8.2.2
* Gradle v5.6.4
* Docker v19.03.4
* docker-compose v1.22.0
* Elsticsearch & Kibana v7.4.2
* Git v2.17.1
* Python v3.6.8

## Setting up the project

### Cloning the repository

```bash
git clone https://github.com/bilalelhoudaigui/books-finder.git
cd books-finder
```

### Running Elasticsearch and Kibana (No Logstash for the moment)

```bash
docker-compose up
```

The version can be changed in the `.env` file.

### Building and Running the App

To build the app, run:

```bash
gradlew assemble
```

The command above will build a standalone `jar` that you can run by typing:

```bash
java -jar backend/build/libs/backend-0.1.jar
```

Or you can simply do both at the same time:

```bash
./gradlew assemble && java -jar backend/build/libs/backend-0.1.jar
```

### Loading the data

* CSV file is located in `data` directory.
* A python script is used to load the data into ES

#### How to

> Python version used is 3.6.8, if you have both puthon 2.x and 3.x on your OS, use `python3` instead of `python` (e.g. `python3 -m venv venv`)

Create a virtualenv:

```bash
cd scripts
python -m venv books-venv
```

If you're on Ubuntu, you may need to install python3-env, if it's not already installed (`sudo apt-get install python3-venv`)

Then Activate it:

```bash
source venv/bin/activate
```

Or on Windows cmd:

```cmd
py -3 -m venv venv
venv\Scripts\activate.bat
```

Install dependencies:

```bash
pip install -r requirements.txt
```

Finally run the script by typing:

```bash
python load_csv.py
```

## Aknowledgment and useful links

* Thanks to @gurtjun for the concise article on [how to intergrate Angular in Spring Boot using Gradle](https://ordina-jworks.github.io/architecture/2018/10/12/spring-boot-angular-gradle.html).
* The dataset [goodreadsbooks](https://www.kaggle.com/jealousleopard/goodreadsbooks) is downloaded from kaggle, thanks to [Soumik](https://www.kaggle.com/jealousleopard)

## TODO

### Improvments

#### Data

##### FIX ERROR: Logstash in docker-compose not reaching elasticsearch

* [X] Download Logstash zip and verify the config file ===> it worked using Logstash outside docker-compose!
* [X] Then verify docker compose ===> pfff it didn't, got: [Logstash giving Elasticsearch Unreachable error](https://stackoverflow.com/questions/53121549/logstash-giving-elasticsearch-unreachable-error)
* [ ] Test docker elk AIO ===> More complicated see the first answer of the error above.
* [X] Create bash/python script which convert the csv to a bulk file ===> DONE!
  * [X] add `how to` to the README

##### OTHERS

* [ ] Try ingesting data using logstash ? (See FIX ERROR above)
* [ ] Check out this bigger dataset: [goodbooks-10k](https://www.kaggle.com/zygmunt/goodbooks-10k#books.csv)

### Backend

* [ ] ...

### Frontend

* [ ] ...
