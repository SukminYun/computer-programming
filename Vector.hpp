#pragma once

#include <cstddef>

namespace CP {
template <typename T> class Vector {
public:
  class Iterator {
  public:
    Iterator(Vector *vec) : vec_(vec), idx_(0){};
    Iterator(Vector *vec, size_t idx) : vec_(vec),idx_(idx){};
    
    Iterator(const Iterator &iter):vec_(iter.vec_),idx_(iter.idx_){};
    
    Iterator &operator=(const Iterator &iter){
      if (this!=&iter){
        vec_=iter.vec_;
        idx_=iter.idx_;
      }
      return *this;
    };
    
    T &operator*() {return vec_->data_[idx_];}

    bool operator==(const Iterator &iter) const {
      return (vec_==iter.vec_ && idx_==iter.idx_);
    }
    bool operator!=(const Iterator &iter) const {
      return (vec_!=iter.vec_||idx_!=iter.idx_);
    }
    
    Iterator &operator++(){
      ++idx_;
      return *this;
    }
    Iterator operator++(int){
      Iterator tmp = *this;
      ++(*this);
      return tmp;
    }
    Iterator &operator--(){
      --idx_;
      return *this;
    }
    Iterator operator--(int){
      Iterator tmp = *this;
      --(*this);
      return tmp;
    };

  private:
    Vector *vec_;
    size_t idx_;
  };

  class ReverseIterator {
  public:
    ReverseIterator(Vector *vec):vec_(vec),idx_(0) {}
    ReverseIterator(Vector *vec, size_t idx):vec_(vec),idx_(idx){}

    ReverseIterator(const ReverseIterator &iter):vec_(iter.vec_),idx_(iter.idx_){};

    ReverseIterator &operator=(const ReverseIterator &iter){
      if (this!=&iter){
        vec_=iter.vec_;
        idx_=iter.idx_;
      }
      return *this;
    }

    T &operator*() {return vec_->data_[idx_];};

    bool operator==(const ReverseIterator &iter) const{
      return (vec_==iter.vec_&&idx_==iter.idx_);
    }
    bool operator!=(const ReverseIterator &iter) const {
      return !(*this==iter);
      };

    ReverseIterator &operator++(){
      --idx_;
      return *this;
    }
    ReverseIterator operator++(int){
      ReverseIterator tmp = *this;
      ++(*this);
      return tmp;
    }
    ReverseIterator &operator--(){
      ++idx_;
      return *this;
    }
    ReverseIterator operator--(int){
      ReverseIterator tmp = *this;
      --(*this);
      return tmp;
    }

  private:
    Vector *vec_;
    size_t idx_;
  };

  Vector() : data_(nullptr), size_(0), capacity_(0) {};
  ~Vector() {
    delete[] data_;
    data_ = nullptr;
  }
  Vector(const Vector &vec) : size_(vec.size_), capacity_(vec.capacity_){
    data_ = new T[capacity_];
    for (size_t i=0; i<size_;++i){
      data_[i]=vec.data_[i];
    }
  }
  Vector &operator=(const Vector &vec){
    if (this != &vec) {
      delete[] data_;
      size_ = vec.size_;
      capacity_ = vec.capacity_;
      data_ = new T[capacity_];
      for (size_t i = 0; i < size_; ++i) {
        data_[i] = vec.data_[i];
      }
    }
    return *this;
  }

  T &operator[](size_t idx){return data_[idx];}

  Iterator begin(){
    return Iterator(this);
  }
  Iterator end(){
    return Iterator(this, size_);
  }
  ReverseIterator rbegin(){
    return ReverseIterator(this, size_ > 0 ? size_- 1:0);
  }
  ReverseIterator rend(){
    return ReverseIterator(this, -1);
  }

  size_t size() const {
    return size_;
  }
  size_t capacity() const{
    return capacity_;
  }

  void reserve(size_t capacity){
    if (capacity_ < capacity) {
      T *newData = new T[capacity];
      for (size_t i = 0; i < size_; ++i) {
        newData[i] = data_[i];
      }
      delete[] data_;
      data_ = newData;
      capacity_ = capacity;
    }
  }

  void push_back(T &data){
    if (size_ == capacity_) {
        size_t newCapacity = (capacity_ == 0) ? 1 : capacity_ * 2;
        reserve(newCapacity);
    }
    data_[size_] = data;
    ++size_;
  }
  void pop_back(){
    if (size_ > 0) {
        --size_;
    }
  }

private:
  T *data_;
  size_t size_;
  size_t capacity_;
};
} // namespace CP