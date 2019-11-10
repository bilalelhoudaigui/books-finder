from elasticsearch import helpers, Elasticsearch
import csv


def read_mapping():
    try:
        with open('books-mapping.json', 'r') as file:
            mapping = file.read().replace('\n', '')
        return mapping
    except Exception as e:
        print(e)


es = Elasticsearch()
index_name = 'books'
mapping = read_mapping()

try:
    es.indices.create(index=index_name, ignore=400, body=mapping)
    with open('../data/books.csv') as f:
        reader = csv.DictReader(f)
        helpers.bulk(es, reader, index=index_name) #, doc_type='my-type'
    print("Loading done successfully!")
except Exception as e:
    print(e)
