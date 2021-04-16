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
import st.proximy.memoize.supplier.ByteSupplier;

/**
 * A simple {@link Memoizable} for a {@code byte}.
 */
public final class MemoizableByte implements Memoizable<@NonNull Byte> {
  private final @NonNull ByteSupplier byteSupplier;
  private boolean hasMemoized = false;
  private byte memoizedValue;

  public MemoizableByte(final @NonNull ByteSupplier byteSupplier) {
    this.byteSupplier = byteSupplier;
  }

  public MemoizableByte(final @NonNull Supplier<@NonNull Byte> byteSupplier) {
    this.byteSupplier = byteSupplier::get;
  }

  public MemoizableByte(final byte memoizedValue) {
    this.byteSupplier = () -> memoizedValue;
    this.hasMemoized = true;
    this.memoizedValue = memoizedValue;
  }

  /**
   * {@inheritDoc}
   *
   * @see #evaluateDataAsByte()
   */
  @Override
  public @NonNull Byte evaluateData() {
    return this.evaluateDataAsByte();
  }

  /**
   * Evaluates the data like {@link #evaluateData()}, except with a primitive. See that method for more detail.
   *
   * @return the now memoized value.
   * @see #evaluateData()
   */
  public byte evaluateDataAsByte() {
    this.hasMemoized = true;
    this.memoizedValue = this.byteSupplier.getAsByte();
    return this.memoizedValue;
  }

  /**
   * {@inheritDoc}
   *
   * @see #memoizedAsByte()
   */
  @Override
  public @NonNull Byte memoized() {
    return this.memoizedAsByte();
  }

  /**
   * Returns the memoized value, or evaluates one then returns it if there is none currently memoized, just like in
   * {@link #memoized()} except with a primitive. See {@link #memoized()} for more detail.
   *
   * @return the now memoized value.
   * @see #memoized()
   */
  public byte memoizedAsByte() {
    if (!this.hasMemoized) {
      return this.evaluateData();
    }

    return this.memoizedValue;
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
