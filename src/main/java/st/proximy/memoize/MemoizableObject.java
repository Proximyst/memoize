//
// memoize - Memoize the return values of an expensive, dynamic state method.
// Copyright (C) 2021 Mariell Hoversholm
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published
// by the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with this program.  If not, see <https://www.gnu.org/licenses/>.
//

package st.proximy.memoize;

import java.util.function.Supplier;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.common.returnsreceiver.qual.This;

/**
 * A very simple {@link Memoizable} for a given type {@code T}.
 *
 * @param <T> the type of the memoizable value.
 */
public final class MemoizableObject<T> implements Memoizable<T> {
  private final @NonNull Supplier<T> supplier;
  private boolean hasMemoized = false;
  private T memoizedValue;

  public MemoizableObject(final @NonNull Supplier<T> supplier) {
    this.supplier = supplier;
  }

  public MemoizableObject(final T memoizedValue) {
    this.supplier = () -> memoizedValue;
    this.memoizedValue = memoizedValue;
    this.hasMemoized = true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public T evaluateData() {
    this.hasMemoized = true;
    this.memoizedValue = this.supplier.get();
    return this.memoizedValue;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public T memoized() {
    if (!this.hasMemoized) {
      return this.evaluateData();
    }

    return this.memoizedValue;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public @NonNull @This MemoizableObject<T> eager() {
    this.evaluateData();
    return this;
  }

  /**
   * Get whether there is currently a memoized value within this.
   *
   * @return whether there is a memoized value.
   */
  public boolean hasMemoized() {
    return this.hasMemoized;
  }
}
