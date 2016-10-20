import json
from pprint import pprint
import gzip
import csv
import pickle
import collections
from pandas import DataFrame as df
import pandas as pd
from sklearn.model_selection import train_test_split

import filepath as fp


def start():
    #save_category_ids(cat_list=["Mice", "Keyboards", "Routers"], out_file=fp.amazon_category_pickle)
    #category_dictionary = pickle.load(open(fp.amazon_category_pickle, "rb"))
    #save_category_reviews(category_dictionary, fp.amazon_category_review)
    create_test_train(csv_file=fp.amazon_category_review, out_train=fp.amazon_review_train,
                      out_test=fp.amazon_review_test, test_per=0.30)


def create_test_train(csv_file = fp.amazon_category_review, out_train=None, out_test=None, test_per=None):
    review_df = df.from_csv(open(csv_file))
    review_keyboard_df = review_df[review_df['category'] == 'Keyboards']
    review_mice_df = review_df[review_df['category'] == 'Mice']
    review_routers_df = review_df[review_df['category'] == 'Routers']

    keyboard_neg = review_keyboard_df[(review_keyboard_df['overall'] < 3)]
    keyboard_other = review_keyboard_df[(review_keyboard_df['overall'] > 2)]

    mice_neg = review_mice_df[(review_mice_df['overall'] < 3)]
    mice_other = review_mice_df[(review_mice_df['overall'] > 2)]

    router_neg = review_routers_df[(review_routers_df['overall'] < 3)]
    router_other = review_routers_df[(review_routers_df['overall'] > 2)]

    keyboard_neg_train, keyboard_neg_test = train_test_split(keyboard_neg, test_size=test_per)
    mice_neg_train, mice_neg_test = train_test_split(mice_neg, test_size=test_per)
    router_neg_train, router_neg_test = train_test_split(router_neg, test_size=test_per)

    keyboard_other_train, keyboard_other_test = train_test_split(keyboard_other, test_size=test_per)
    mice_other_train, mice_other_test = train_test_split(mice_other, test_size=test_per)
    router_other_train, router_other_test = train_test_split(router_other, test_size=test_per)

    #train_df = keyboard_neg_train
    #print(train_df.head())

    train_df = [keyboard_neg_train, mice_neg_train, router_neg_train,
                keyboard_other_train, mice_other_train, router_other_train]
    train_result = pd.concat(train_df)
    train_result.to_csv(out_train)

    test_df = [keyboard_neg_test , mice_neg_test,  router_neg_test,
               keyboard_other_test , mice_other_test, router_other_test]
    test_result = pd.concat(test_df)
    test_result.to_csv(out_test)


def parse(filename):
    f = gzip.open(filename, 'r')
    for l in f:
        yield json.dumps(eval(l))


def save_category_reviews(category_dict, out_file):
    count = 0
    csv_writer = csv.writer(open(out_file, "w"))
    csv_writer.writerow(['review_id', 'asin', 'summary', 'reviewText', 'overall', 'category'])
    for e in parse(fp.amazon_electronics_filepath):
        json_obj = json.loads(e)
        review_id = str(json_obj['unixReviewTime']) + str(json_obj['reviewerID'])
        asin = json_obj['asin']
        overall = json_obj['overall']
        reviewText = json_obj['reviewText']
        summary = json_obj['summary']
        for category in category_dict.keys():
            ids = category_dict[category]
            if asin in ids:
                count += 1
                csv_writer.writerow([review_id, asin, summary, reviewText, overall, category])


def save_category_ids(cat_list, out_file):
    category_dict = {key: [] for key in cat_list}
    counter_list = []
    for e in parse("/home/trideep/Desktop/data/meta_Electronics.json.gz"):
        json_obj = json.loads(e)
        asin = json_obj['asin']
        categories = json_obj['categories'][0]
        for key in cat_list:
            if key in categories:
                counter_list.append(key)
                category_dict[key].append(str(asin))
    counter = collections.Counter(counter_list)
    pickle.dump(category_dict, open(out_file, "wb"))
    print(counter)
    return category_dict, counter




start()
